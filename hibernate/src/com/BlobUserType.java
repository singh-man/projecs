package com;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.*;

public class BlobUserType implements UserType {

	private int[] types = {Types.BLOB};

	public int[] sqlTypes() {
		return types;
	}

	public Class returnedClass() {
		return Blob.class;
	}

	@Override
	public Object assemble(Serializable arg0, Object arg1)
	throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deepCopy(Object value) {
		if (value == null) {
			return null;
		} else {
			byte[] data = null;

			byte[] buff = new byte[2 * 1024];
			InputStream in = null;
			try {
				in = ((Blob) value).getBinaryStream();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int count = 0;
			int n = 0;
			StringBuffer sb = new StringBuffer();
			try {
				if(in != null)
					while(-1 != (n = in.read(buff))) {
						sb.append(buff);
						count += n;
					}
			} catch (IOException e) {
				e.printStackTrace();
			}

			data = sb.toString().getBytes();

			byte[] bytes = data;
			byte[] result = new byte[bytes.length];
			System.arraycopy(bytes, 0, result, 0, bytes.length);
			return result;
		}
	}

	@Override
	public Serializable disassemble(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object x, Object y) {  
		return (x == y) || (x != null && y != null && 
				java.util.Arrays.equals(convertBlobToByteArray(((SerialBlob) y)), ((byte[]) x)));  
	} 

	@Override
	public int hashCode(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isMutable() {
		// TODO Auto-generated method stub
		return false;
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object o)
	throws HibernateException, SQLException {

		byte[] data = null;

		LobHandler lob = new DefaultLobHandler();
		data = lob.getBlobAsBytes(rs, names[0]);
		/*byte[] buff = new byte[2 * 1024];
		InputStream in = rs.getBinaryStream(names[0]);
		int count = 0;
		int n = 0;
		StringBuffer sb = new StringBuffer();
		try {
			if(in != null)
				while(-1 != (n = in.read(buff))) {
					sb.append(buff);
					count += n;
				}
		} catch (IOException e) {
			e.printStackTrace();
		}

		data = sb.toString().getBytes();*/

		Blob blob = new SerialBlob(data);

		/*Blob data = rs.getBlob(names[0]);*/

		return blob;
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index)
	throws HibernateException, SQLException {

		System.out.println("hello");
		Connection con = st.getConnection();

		/*try {
			System.out.println("in try");
			//postgres
			st.setBlob(index, Hibernate.createBlob((byte[]) value));
		} catch(Exception ex) {
			//sybase, oracle, mysql
			st.setBytes(index, (byte[])value);
		}*/

		//oracle, mysql
		//ByteArrayInputStream bais = new ByteArrayInputStream((byte[]) value); 	st.setBinaryStream(index, bais);

		//postgres, mysql
		/*for(byte b : (byte[])value) {
			st.setByte(index, b);
		}*/

		org.springframework.jdbc.support.lob.LobCreator lob = new DefaultLobHandler().getLobCreator();
		if (value != null) {
			lob.setBlobAsBinaryStream(st, index, (InputStream) value, -1);
		} else {
			lob.setBlobAsBytes(st, index, null);
		}

	}

	@Override
	public Object replace(Object arg0, Object arg1, Object arg2)
	throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	private byte[] convertBlobToByteArray(Blob blob) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {

			byte[] buf = new byte[1024];

			InputStream in = blob.getBinaryStream();

			int n = 0;
			while ((n=in.read(buf))>=0)
			{
				baos.write(buf, 0, n);

			}

			in.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}

		return baos.toByteArray(); 

	}

}