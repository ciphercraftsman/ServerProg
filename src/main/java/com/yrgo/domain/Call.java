package com.yrgo.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "TBL_CALL")
public class Call {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private Date timeAndDate;

	private String notes;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // Relationshantering för actions
	@JoinColumn(name = "call_id") // Skapar en främmande nyckel i actions-tabellen
	private List<Action> actions = new ArrayList<>(); // Lista för åtgärder kopplade till samtalet

	// Standardkonstruktor
	public Call() {}

	// Konstruktor med bara notes
	public Call(String notes) {
		this(notes, new Date());
	}

	// Konstruktor med notes och timestamp
	public Call(String notes, Date timestamp) {
		this.timeAndDate = timestamp;
		this.notes = notes;
	}

	// Getter och setter för timeAndDate
	public Date getTimeAndDate() {
		return timeAndDate;
	}

	public void setTimeAndDate(Date timeAndDate) {
		this.timeAndDate = timeAndDate;
	}

	// Getter och setter för notes
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	// Getter och setter för actions
	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	// Lägg till en action till samtalet
	public void addAction(Action action) {
		this.actions.add(action);
	}

	// Ta bort en action från samtalet
	public void removeAction(Action action) {
		this.actions.remove(action);
	}

	// toString-metod
	@Override
	public String toString() {
		return "Call{" +
				"id=" + id +
				", timeAndDate=" + timeAndDate +
				", notes='" + notes + '\'' +
				", actions=" + actions +
				'}';
	}
}
