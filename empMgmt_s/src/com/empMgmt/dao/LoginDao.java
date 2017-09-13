package com.empMgmt.dao;

import com.empMgmt.db.DbMsgs;
import com.empMgmt.entity.Employee;

public interface LoginDao extends Dao {

	public Employee authenticate(final String eid, final String password);

	public DbMsgs loginToday(final String eid, final String password);

	public DbMsgs logoutToday(final String eid, final String password);

}
