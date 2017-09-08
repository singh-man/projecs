package com.ericsson.processor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.ericsson.RegisterListener;
import com.ericsson.listener.Listener;
import com.ericsson.subject.Subject;

/**
 * Scans class decorated with 
 * @RegisterListener({<>}) if present will register the listener to provided subject
 * 
 * @author emmhssh
 *
 */
public class AnnotationRegisterListenerProcessor implements BeanPostProcessor {

	private List<Subject> subjectList; 

	@Override
	public Object postProcessAfterInitialization(Object bean, String name)
			throws BeansException {

		Class<? extends Object> clazz = bean.getClass();
		if(bean instanceof Listener && clazz.isAnnotationPresent(RegisterListener.class)) {

			Class<? extends Subject>[] subjectArray = (Class<? extends Subject>[]) clazz.getAnnotation(RegisterListener.class).subject();

			if(subjectArray.length <= 0) {
				//Add this listener to all the subjects
				for(Subject subject : subjectList) {
					subject.addListner((Listener) bean);
				}
				return bean;
			}

			// Handles the duplicacy --- Set is used.  
			Set<Class<? extends Subject>> subjectSet = new HashSet<Class<? extends Subject>>(Arrays.asList(subjectArray));
			
			/*
			 * Resolve the Super Class annotation if the property includeParent is true;
			 * Create a final Set that contains all the subject present on Listener including its 
			 * SuperClass
			 */
			if(clazz.getAnnotation(RegisterListener.class).includeParent()) {
				Class<?> superClass = clazz.getSuperclass();
				while(superClass != Object.class) {
					if(Listener.class.isAssignableFrom(superClass) && superClass.isAnnotationPresent(RegisterListener.class)) {
						Class<? extends Subject>[] superArray = (Class<? extends Subject>[]) superClass.getAnnotation(RegisterListener.class).subject();
						subjectSet.addAll(Arrays.asList(superArray));
					}
					superClass = superClass.getSuperclass();
				}
			}

			// Arr has the file Subjects array that includes all the Super class Subjects too, if applicable
			subjectArray = subjectSet.toArray(new Class[subjectSet.size()]);

			registerListenerToSubjects(bean, subjectArray);
		}
		return bean;
	}

	private void registerListenerToSubjects(Object bean, Class<? extends Subject>[] subjectArray) {
		for(Subject subject : subjectList) {
			if(isPresent(subject, subjectArray)) {
				subject.addListner((Listener) bean);
			}
		}
	}

	private boolean isPresent(Subject subject, Class<? extends Subject>[] subArray) {
		for(int i=0; i < subArray.length; i++) {
			if(subject.getClass() == subArray[i])
				return true;
		}
		return false;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String name)
			throws BeansException {
		return bean;
	}

	public void setSubjectList(List<Subject> subjectList) {
		this.subjectList = subjectList;
	}
}
