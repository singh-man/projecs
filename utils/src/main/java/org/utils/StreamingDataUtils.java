package org.utils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * While handling Streaming Data
 *
 * Check for ByteArray Input/Output Stream
 *
 * that solves most of the problems; almost any stream can be converted or
 * handled by ByteArray Input/Output Stream.
 *
 * The API is strict utility class; Don't close any Stream passed to it.
 *
 * @author emmhssh
 *
 */
public class StreamingDataUtils {

    /**
     * Calculates the size/length of byte coming in InputStream Best suited till
     * 2147483647 = Integer.MAX_VALUE
     *
     * Note: Will destroy the incoming InputStream
     *
     * Deprecated in favour of sizeOfInpStream(InputStream)
     *
     * Also a way to copy InputStream to OutputStream
     */
    @Deprecated
    private static void sizeOfInStream(String msg, InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            transferInputStreamToOutputStream(is, baos);
            System.out.println(msg + " : " + baos.toByteArray().length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the size/length of byte coming in InputStream Best suited till -
     * Long.MAX_VALUE
     *
     * Note: Will destroy the InputStream
     */
    public static void sizeOfInpStream(String msg, InputStream is) {
        try {
            int len = 0;
            long streamLen = 0;
            byte[] buff = new byte[2048];
            while ((len = is.read(buff)) > 1) {
                streamLen += len;
            }
            System.out.println(msg + " : " + streamLen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates the size/length of byte coming in InputStream Best suited till
     * - Long.MAX_VALUE
     *
     * Note: Will not destroy the incoming InputStream. Also returns the new
     * InputStream Restricted by Memory size as the returned stream is of type
     * ByteArrayInputStream
     */
    public static InputStream sizeOfInputStream(String msg, InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            transferInputStreamToOutputStream(is, baos);

            // Open new InputStream using the recorded bytes
            // Can be repeated as many times as you wish
            is = new ByteArrayInputStream(baos.toByteArray());

            System.out.println(msg + " : " + baos.toByteArray().length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }

    public static void sizeOfOutStream(String msg, OutputStream os) {
        //PipedInputStream pis = new PipedInputStream(new PipedOutputStream());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //oos.write
        //ByteArrayInputStream baos = new ByteArrayInputStream(os.)

        //ToDo
    }

    /*
	 * OutputStream chaining can be done only in two streams
	 * The outputStreams that can take other output streams are: - 
	 * 1. ObjectOutputStream(<takes another outputstream in constructor>)
	 * 2. FilterOutputStream - and its sub classes like *BufferedOutputStream*, *DeflatorOutputStream*
	 * 		GZIPOutputStream(<takes another outputstream in constructor>)
     */
    private static void serializeObjectToOutputStream(Object obj, OutputStream os) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /*
	 * And the same is true for InputStream transformation
	 * means:
	 * InputStream that can take from other inputStreams are as follows:
	 * 1. ObjectInputStream(<takes another inputstream in constructor>)
	 * 2. FilterInputStream - and its sub classes like *BufferedinputStream*, *DeflatorinputStream*
	 * 		GZIPInputStream(<takes another inputstream in constructor>)
     */
    private static Object deSerializeInputStreamToObject(InputStream is) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(is);
            return ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ois;
    }

    public static <T> void serializeObjectToFile(T obj, String filePath, String dateInSimpleDateFormat) {
        try {
            dateInSimpleDateFormat = dateInSimpleDateFormat == null ? "none" : dateInSimpleDateFormat;
            File f = new File(filePath + obj.getClass() + "-" + dateInSimpleDateFormat);

            OutputStream os = new FileOutputStream(f);
            serializeObjectToOutputStream(obj, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * File provided should be a Java Object serialized file
     *
     * @param filePath
     */
    public static <T> T deSerializeFileToObject(String filePath) {
        try {
            return (T) deSerializeInputStreamToObject(new FileInputStream(new File(filePath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> ByteArrayOutputStream serializeObjectToByteArrayOutputStream(T obj) {
        OutputStream baos = new ByteArrayOutputStream();
        serializeObjectToOutputStream(obj, baos);
        return (ByteArrayOutputStream) baos;
    }

    public static Object deSerializeByteArrayInputStreamToObject(ByteArrayInputStream is) {
        return deSerializeInputStreamToObject(is);
    }

    /**
     * ByteArrayOutputStream gives byte array directly that can be given to
     * ByteArrayInputStream that takes byte array
     */
    public static Object deSerializeByteArrayOutputStreamToObject(ByteArrayOutputStream baos) {
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return deSerializeInputStreamToObject(bais);
    }

    /**
     * Way to compress an object and write/serialize the object to OutputStream
     * (can be byteArray or FileOutput)
     *
     * @param obj
     * @return
     */
    public static <T> ByteArrayOutputStream serializeAndCompressObjectToByteArrayOutputStream(T obj) {
        try {
            /*gzipstream only works on byte array

				to write to gzip stream
				1. create an output stream 
				2. create a gzip output stream; pass the created output stream in step 1 
				this gzip outputstream constructor
				3. write the data(bytes only) to gzip output stream which in turn will 
				write to the constructor passed outputstream of gzipstrem*/
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gzos = new GZIPOutputStream(baos, 1024);

            serializeObjectToOutputStream(obj, gzos); // Ouput Stream given is Gzip

            /*
			 * Note: Stream Compressed will be ##### ByteArrayOutputStream #####
			 * not Gzip OutputStream -------- 
             */
            return baos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object deSerializeCompressByteArrayInputStreamToObject(ByteArrayInputStream bais) {
        try {
            return deSerializeInputStreamToObject(new GZIPInputStream(bais));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Way to compress an object and write/serialize the object to OutputStream
     * (can be byteArray or FileOutput) With compression level.
     *
     * @param obj
     * @return
     */
    public static <T> ByteArrayOutputStream serializeAndCompressObjectToByteArrayOutputStreamWithCompressionLevel(T obj, final int level) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            /*
			 * Way to access the protected members of the class with actually subclassing it.
			 * 
			 * anonymous inner class with the so-called "anonymous constructor"
			 * 
			 * { } represents instance code here
             */
            GZIPOutputStream gzos = new GZIPOutputStream(baos, 1024) {
                {
                    def.setLevel(level);
                }
            };

            serializeObjectToOutputStream(obj, gzos); // Ouput Stream given is Gzip

            return baos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> void serializeAndCompressObjectToFile(T obj, String filePath, String dateInSimpleDateFormat) {
        try {
            File f = new File(filePath + obj.getClass() + "-compress-" + dateInSimpleDateFormat);

            GZIPOutputStream gos = new GZIPOutputStream(new FileOutputStream(f));
            serializeObjectToOutputStream(obj, gos);
            gos.flush();
            gos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T deSerializeCompressedFileToObject(String filePath) {
        try {
            GZIPInputStream gis = new GZIPInputStream(new FileInputStream(new File(filePath)));
            return (T) deSerializeInputStreamToObject(gis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*public static <T> T deSerializeoutputStreamToObject(OutputStream os) {
		try {
			ObjectInputStream ois = new ObjectInputStream();
			return (T) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
    public static void transferOutputStreamToInputStream(OutputStream os, InputStream is) {
        try {
            if (os instanceof ByteArrayOutputStream && is instanceof ByteArrayInputStream) {
                ((ByteArrayInputStream) is).read(((ByteArrayOutputStream) os).toByteArray());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //ToDo for any Os to Is
    }

    /*
	 * OutputStream chaining can be done only in two streams
	 * The outputStreams that can take other output streams are: - 
	 * 1. ObjectOutputStream(<takes another outputstream in constructor>)
	 * 2. FilterOutputStream - and its sub classes like *BufferedOutputStream*, *DeflatorOutputStream*
	 * 		GZIPOutputStream(<takes another outputstream in constructor>)
     */
    public static InputStream transferOutputStreamToOutputStream(OutputStream os1, OutputStream os2) {
        //ToDo for any Os to Os
        return null;
    }

    public static void transferInputStreamToOutputStream(InputStream is, OutputStream os) {
        int len = 0;
        try {
            byte[] buff = new byte[2048];
            while ((len = is.read(buff)) > 1) {
                os.write(buff, 0, len);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static long sizeOfObject(String msg, Object obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        serializeObjectToOutputStream(obj, baos);
        System.out.println(msg + " : " + baos.toByteArray().length);
        return baos.toByteArray().length;
    }

    public static void xmlSerializationToFile(Object o, String filePath, String dateInSimpleDateFormat) {
        try {
            dateInSimpleDateFormat = dateInSimpleDateFormat == null ? "none" : dateInSimpleDateFormat;
            File f = new File(filePath + o.getClass() + "-" + dateInSimpleDateFormat);
            XMLEncoder encoder = new XMLEncoder(new FileOutputStream(f));
            //For performance XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(f)));
            encoder.writeObject(o);
            encoder.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static <T> T xmlDeSerializationFrom(String filePath) {
        try {
            File f = new File(filePath);
            XMLDecoder decoder = new XMLDecoder(new FileInputStream(f));
            return (T) decoder.readObject();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
