package com.nikitachizhik91.university.web.servlets.lessons;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nikitachizhik91.university.domain.DomainException;
import com.nikitachizhik91.university.domain.GroupManager;
import com.nikitachizhik91.university.domain.LessonManager;
import com.nikitachizhik91.university.domain.RoomManager;
import com.nikitachizhik91.university.domain.SubjectManager;
import com.nikitachizhik91.university.domain.TeacherManager;
import com.nikitachizhik91.university.domain.impl.GroupManagerImpl;
import com.nikitachizhik91.university.domain.impl.LessonManagerImpl;
import com.nikitachizhik91.university.domain.impl.RoomManagerImpl;
import com.nikitachizhik91.university.domain.impl.SubjectManagerImpl;
import com.nikitachizhik91.university.domain.impl.TeacherManagerImpl;
import com.nikitachizhik91.university.model.Group;
import com.nikitachizhik91.university.model.Lesson;
import com.nikitachizhik91.university.model.Room;
import com.nikitachizhik91.university.model.Subject;
import com.nikitachizhik91.university.model.Teacher;
import com.nikitachizhik91.university.web.WebException;

@WebServlet("/lesson")
public class LessonServlet extends HttpServlet {

	private final static Logger log = LogManager.getLogger(LessonServlet.class.getName());
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.trace("Started findById() method.");

		String lessonId = request.getParameter("lessonId");
		Lesson lesson = null;
		LessonManager lessonManager = new LessonManagerImpl();

		List<Subject> subjects = new ArrayList<Subject>();
		SubjectManager subjectManager = new SubjectManagerImpl();

		GroupManager groupManager = new GroupManagerImpl();
		List<Group> groups = null;

		List<Teacher> teachers = null;
		TeacherManager teacherManager = new TeacherManagerImpl();

		List<Room> rooms = null;
		RoomManager roomManager = new RoomManagerImpl();

		try {
			lesson = lessonManager.findById(Integer.parseInt(lessonId));

			subjects = subjectManager.findAll();

			groups = groupManager.findAll();

			teachers = teacherManager.findAll();

			rooms = roomManager.findAll();

		} catch (NumberFormatException e) {
			log.error("The id=" + lessonId + " is wrong.", e);
			throw new WebException("The id=" + lessonId + " is wrong.", e);

		} catch (DomainException e) {
			log.error("Cannot find lesson by id=" + lessonId, e);
			throw new WebException("Cannot find lesson by id=" + lessonId, e);
		}

		int[] numbers = { 1, 2, 3, 4, 5 };

		request.setAttribute("rooms", rooms);
		request.setAttribute("teachers", teachers);
		request.setAttribute("groups", groups);
		request.setAttribute("numbers", numbers);
		request.setAttribute("lesson", lesson);
		request.setAttribute("subjects", subjects);

		request.getRequestDispatcher("/lesson.jsp").forward(request, response);

		log.trace("Finished findById() method.");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.trace("Started update() method.");

		String lessonId = request.getParameter("lessonId");
		String number = request.getParameter("number");
		String subjectId = request.getParameter("subjectId");
		String groupId = request.getParameter("groupId");
		String teacherId = request.getParameter("teacherId");
		String roomId = request.getParameter("roomId");
		String dateString = request.getParameter("date");

		Lesson lesson = null;
		LessonManager lessonManager = new LessonManagerImpl();
		SubjectManager subjectManager = new SubjectManagerImpl();
		GroupManager groupManager = new GroupManagerImpl();
		TeacherManager teacherManager = new TeacherManagerImpl();
		RoomManager roomManager = new RoomManagerImpl();

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = null;
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			log.error("Date=" + date + " is wrong.", e);
			throw new WebException("Date=" + date + " is wrong.", e);
		}

		try {
			lesson = lessonManager.findById(Integer.parseInt(lessonId));

			Subject subject = subjectManager.findById(Integer.parseInt(subjectId));
			lesson.setSubject(subject);

			Group group = groupManager.findById(Integer.parseInt(groupId));
			lesson.setGroup(group);

			Teacher teacher = teacherManager.findById(Integer.parseInt(teacherId));
			lesson.setTeacher(teacher);

			Room room = roomManager.findById(Integer.parseInt(roomId));
			lesson.setRoom(room);

			lesson.setNumber(Integer.parseInt(number));

			lesson.setDate(date);

			lessonManager.update(lesson);

		} catch (DomainException e) {
			log.error("Cannot update lesson=" + lesson, e);
			throw new WebException("Cannot update lesson=" + lesson, e);

		} catch (NumberFormatException e) {
			log.error("The id=" + lessonId + " is wrong.", e);
			throw new WebException("The id=" + lessonId + " is wrong.", e);
		}

		response.sendRedirect("lesson?lessonId=" + lessonId);

		log.trace("Finished update() method.");
	}

}
