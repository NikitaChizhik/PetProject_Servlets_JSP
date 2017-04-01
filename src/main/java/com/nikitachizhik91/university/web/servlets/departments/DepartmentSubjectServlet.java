package com.nikitachizhik91.university.web.servlets.departments;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nikitachizhik91.university.domain.DepartmentManager;
import com.nikitachizhik91.university.domain.DomainException;
import com.nikitachizhik91.university.domain.impl.DepartmentManagerImpl;
import com.nikitachizhik91.university.web.WebException;

@WebServlet("/departmentSubject")
public class DepartmentSubjectServlet extends HttpServlet {

	private final static Logger log = LogManager.getLogger(DepartmentSubjectServlet.class.getName());
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.trace("Started deleteSubjectFromDepartment() method.");

		DepartmentManager departmentManager = new DepartmentManagerImpl();
		String subjectId = request.getParameter("subjectId");
		String departmentId = request.getParameter("departmentId");

		try {
			departmentManager.deleteSubjectFromDepartment(Integer.parseInt(subjectId));

		} catch (NumberFormatException e) {
			log.error("The id=" + subjectId + " is wrong.", e);
			throw new WebException("The id=" + subjectId + " is wrong.", e);

		} catch (DomainException e) {
			log.error("Cannot delete subject with id=" + subjectId + " from group with id=" + departmentId, e);
			throw new WebException("Cannot delete subject with id=" + subjectId + " from group with id=" + departmentId,
					e);
		}

		response.sendRedirect("department?departmentId=" + departmentId);

		log.trace("Finished deleteSubjectFromDepartment() method.");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.trace("Started addSubject() method.");

		String subjectId = request.getParameter("subjectId");
		String departmentId = request.getParameter("departmentId");
		DepartmentManager departmentManager = new DepartmentManagerImpl();

		try {
			departmentManager.addSubject(Integer.parseInt(departmentId), Integer.parseInt(subjectId));

		} catch (DomainException e) {
			log.error("Cannot add subject with id=" + subjectId + " to department with id=" + departmentId, e);
			throw new WebException("Cannot add subject with id=" + subjectId + " to department with id=" + departmentId,
					e);

		} catch (NumberFormatException e) {
			log.error("The id=" + departmentId + " is wrong.", e);
			throw new WebException("The id=" + departmentId + " is wrong.", e);
		}
		response.sendRedirect("department?departmentId=" + departmentId);

		log.trace("Finished addSubject() method.");
	}
}