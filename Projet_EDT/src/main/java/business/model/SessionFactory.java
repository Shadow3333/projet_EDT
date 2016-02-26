package business.model;

import java.lang.reflect.MalformedParametersException;
import java.util.Date;

import business.dao.IDao;
import business.model.EU.LessonType;
import business.model.users.AbstractUser;
import business.model.users.Teacher;

/**
 * This class is a factory to Sesion
 * @author DUBUIS Michael
 *
 */
public class SessionFactory {
	private IDao dao;
	
	private String idEU = null;
	private Courses courses = null;
	private Date date = null;
	private Integer nbHour = null;
	private String room = null;
	private LessonType type = null;
	private Integer numGroup = null;
	private String teacher = null;
	
	/**
	 * Empty constructor
	 */
	SessionFactory() {}
	
	/**
	 * Constructor with IDao 
	 * @param dao
	 */
	public SessionFactory(IDao dao) {
		this.dao = dao;
	}

	/**
	 * @return the idEU
	 */
	public String getIdEU() {
		return idEU;
	}

	/**
	 * @param idEU the idEU to set
	 */
	public void setIdEU(String idEU) {
		this.idEU = idEU;
	}

	/**
	 * @return the courses
	 */
	public Courses getCourses() {
		return courses;
	}

	/**
	 * @param courses the courses to set
	 */
	public void setCourses(Courses courses) {
		this.courses = courses;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the nbHour
	 */
	public Integer getNbHour() {
		return nbHour;
	}

	/**
	 * @param nbHour the nbHour to set
	 */
	public void setNbHour(Integer nbHour) {
		this.nbHour = nbHour;
	}

	/**
	 * @return the room
	 */
	public String getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(String room) {
		this.room = room;
	}

	/**
	 * @return the type
	 */
	public LessonType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(LessonType type) {
		this.type = type;
	}

	/**
	 * @return the numGroup
	 */
	public Integer getNumGroup() {
		return numGroup;
	}

	/**
	 * @param numGroup the numGroup to set
	 */
	public void setNumGroup(Integer numGroup) {
		this.numGroup = numGroup;
	}

	/**
	 * @return the teacher
	 */
	public String getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher the teacher to set
	 */
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	/**
	 * Create a session CM without teacher
	 * @param dao
	 * @param courses
	 * @param date
	 * @param nbHour
	 * @param room
	 * @return
	 * @throws Exception
	 */
	public static Session createSession(
			IDao dao,
			Courses courses,
			String idEU,
			Date date,
			Integer nbHour,
			String room
			) throws Exception {
		return createSession(
				dao, courses, idEU, date, nbHour, room, null, null, null);
	}
	
	/**
	 * Create a session CM
	 * @param dao
	 * @param courses
	 * @param date
	 * @param nbHour
	 * @param room
	 * @param teacher
	 * @return
	 * @throws Exception
	 */
	public static Session createSession(
			IDao dao,
			Courses courses,
			String idEU,
			Date date,
			Integer nbHour,
			String room,
			String teacher
			) throws Exception {
		return createSession(
				dao, courses, idEU, date, nbHour, room, null, null, teacher);
	}
	
	/**
	 * Create a session
	 * @param dao
	 * @param courses
	 * @param date
	 * @param nbHour
	 * @param room
	 * @param type
	 * @param numGroup
	 * @param teacher
	 * @return
	 * @throws Exception
	 */
	public static Session createSession(
			IDao dao,
			Courses courses,
			String idEU,
			Date date,
			Integer nbHour,
			String room,
			LessonType type,
			Integer numGroup,
			String teacher
			) throws Exception {
		SessionFactory factory = new SessionFactory(dao);
		factory.courses = courses;
		factory.idEU = idEU;
		factory.date = date;
		factory.nbHour = nbHour;
		factory.room = room;
		factory.type = type;
		factory.numGroup = numGroup;
		factory.teacher = teacher;
		return factory.createSession();
	}
	
	/**
	 * Create a session
	 * @return
	 * @throws Exception - if parameters aren't well formed
	 */
	public Session createSession() throws Exception {
		Boolean bool = false;
		String message = "One or more parameters are malformed :\n";
		if(date == null) {
			message += "\t- date is missing\n";
			bool = true;
		}
		if(courses == null) {
			message += "\t- courses is missing\n";
			bool = true;
		}
		if(idEU == null) {
			message += "\t- EU is missing\n";
			bool = true;
		}
		if(nbHour == null) {
			message += "\t- number of hours is missing\n";
			bool = true;
		}
		if(nbHour != null && nbHour <= 0) {
			message += "\t- number of hours must be greater than 0\n";
			bool = true;
		}
		if(room == null) {
			message += "\t- room is missing\n";
			bool = true;
		}

		if(type == null) {
			type = LessonType.CM;
		}
		if(!type.equals(LessonType.CM) && numGroup == null) {
			message += "\t- LessonType and numGroup not compatible !\n";
			bool = true;
		}
		if(bool) {
			throw new MalformedParametersException(message);
		}
		
		Session s = new Session();
		EU eu = dao.find(EU.class, idEU);
		if(eu == null){
			throw new IllegalArgumentException(
					"This ID for EU doesn't exists !");
		}
		if(!courses.getEUs().contains(eu)) {
			throw new IllegalArgumentException(
					"This courses doesn't contains this EU !");
		}
		GroupEU groupEU = courses.getGroupEUWhoContains(eu);
		if(type.equals(LessonType.CM)) {
			s.setGroupStudent(groupEU.getCm());
		} else if(type.equals(LessonType.TD)) {
			s.setGroupStudent(groupEU.getTd().get(numGroup));
		} else if(type.equals(LessonType.TP)) {
			s.setGroupStudent(groupEU.getTp().get(numGroup));
		} else {
			throw new Exception("NOT YET !");
		}
		if(s.getGroupStudent() == null) {
			throw new IllegalArgumentException(
					"This groupEU doesn't exist !");
		}
		s.setEu(eu);
		s.setDate(date);
		s.setNbHour(nbHour);
		s.setType(type);
		if(teacher != null) {
			AbstractUser t =
					(AbstractUser) dao.find(Teacher.class, teacher);
			if(t == null) {
				throw new IllegalArgumentException("Teacher not found !");
			}
			if(type.equals(LessonType.CM)
					&& !((Teacher) t).getCm().contains(eu)
					|| type.equals(LessonType.TD)
					&& !((Teacher) t).getTd().contains(eu)
					|| type.equals(LessonType.TP)
					&& !((Teacher) t).getTp().contains(eu)) {
				throw new IllegalArgumentException(
						"This user doesn't teach "
								+ eu.getId() + " in " +
								type.toString());
			}
			s.setTeacher(t);
		}
		return s;
	}
}
