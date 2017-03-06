package com.nikitachizhik91.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.nikitachizhik91.university.model.Faculty;

public class FacultyDAOImpl {

	private static final String INSERT_FACULTY = "insert into faculties (name) values(?)";
	private static final String FIND_FACULTY_BY_ID = "select * from faculties where id=?";
	private static final String FIND_ALL_FACULTIES = "select * from faculties";
	private static final String UPDATE_FACULTY = "update faculties set name=? where id =?";
	private static final String DELETE_FACULTY = "delete from faculties where id =?";

	public Faculty create(Faculty faculty) {
		Faculty facultyReceived = null;
		Connector connector = new Connector();
		try (Connection connection = connector.getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT_FACULTY,
						Statement.RETURN_GENERATED_KEYS);) {

			statement.setString(1, faculty.getName());
			statement.executeUpdate();

			try (ResultSet resultSet = statement.getGeneratedKeys();) {
				while (resultSet.next()) {
					facultyReceived = new Faculty();
					facultyReceived.setId(resultSet.getInt("id"));
					facultyReceived.setName(resultSet.getString("name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return facultyReceived;
	}

	public Faculty findById(int id) {
		Faculty facultyReceived = new Faculty();
		Connector connector = new Connector();
		try (Connection connection = connector.getConnection();

		PreparedStatement statement = connection.prepareStatement(FIND_FACULTY_BY_ID)) {

			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {

					facultyReceived.setId(resultSet.getInt("id"));
					facultyReceived.setName(resultSet.getString("name"));
				}
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return facultyReceived;
	}

	public List<Faculty> findAll() {
		List<Faculty> facultiesReceived = new ArrayList<Faculty>();
		Connector connector = new Connector();
		try (Connection connection = connector.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_ALL_FACULTIES);
				ResultSet resultSet = statement.executeQuery();) {

			while (resultSet.next()) {
				Faculty faculty = new Faculty();
				faculty.setId(resultSet.getInt("id"));
				faculty.setName(resultSet.getString("name"));
				facultiesReceived.add(faculty);
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return facultiesReceived;
	}

	public Faculty update(Faculty faculty) {
		Faculty facultyReceived = null;
		Connector connector = new Connector();
		try (Connection connection = connector.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_FACULTY,
						Statement.RETURN_GENERATED_KEYS);) {

			statement.setString(1, faculty.getName());
			statement.setInt(2, faculty.getId());
			statement.executeUpdate();

			try (ResultSet resultSet = statement.getGeneratedKeys();) {
				while (resultSet.next()) {
					facultyReceived = new Faculty();
					facultyReceived.setId(resultSet.getInt("id"));
					facultyReceived.setName(resultSet.getString("name"));
				}
			}

		} catch (SQLException e) {
			e.getMessage();
		}
		return facultyReceived;
	}

	public void delete(int id) {
		Connector connector = new Connector();
		try (Connection connection = connector.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_FACULTY);) {

			statement.setInt(1, id);

			statement.executeUpdate();

		} catch (SQLException e) {
			e.getMessage();
		}
	}

}
