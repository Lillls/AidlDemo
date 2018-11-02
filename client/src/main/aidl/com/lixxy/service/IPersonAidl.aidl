// IBookAidl.aidl
package com.lixxy.service;
import com.lixxy.service.Person;

interface IPersonAidl {

   List<Person> getList();
   void addPerson(in Person person);
}
