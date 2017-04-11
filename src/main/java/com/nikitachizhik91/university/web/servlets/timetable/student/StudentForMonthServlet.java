package com.nikitachizhik91.university.web.servlets.timetable.student;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nikitachizhik91.university.domain.DomainException;
import com.nikitachizhik91.university.domain.StudentManager;
import com.nikitachizhik91.university.domain.impl.StudentManagerImpl;
import com.nikitachizhik91.university.model.Student;
import com.nikitachizhik91.university.web.WebException;

@WebServlet("/studentTimetableForMonth")
public class StudentForMonthServlet extends HttpServlet {

	private final static Logger log = LogManager.getLogger(StudentForMonthServlet.class.getName());
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.trace("Started studentTimetableForMonth servlet.");

		List<Student> students = null;
		StudentManager studentManager = new StudentManagerImpl();

		try {
			students = studentManager.findAll();

		} catch (DomainException e) {

			log.error("Cannot find all students.", e);
			throw new WebException("Cannot find all students.", e);
		}

		request.setAttribute("students", students);
		request.getRequestDispatcher("/findStudentTimetableForMonth.jsp").forward(request, response);

		log.trace("Finished studentTimetableForMonth servlet.");
	}

}