package com.example.testui.model;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.example.testui.R;

import java.time.LocalDate;
import java.util.List;

public class ProjectTerm {
    private String id;
    private String academy_year_id;
    private String stage;
    private String description;
    private String start_date;
    private String end_date;
    private AcademyYear academy_year;
    private List<StageTimeline> stage_timelines;
    private List<Assignment> assignments;
    private List<Supervisor> supervisors;
    private List<Council> councils;
    private int backgroundColor;
    private String created_at;
    private String updated_at;
    private Boolean isRegistered;
    private String registeredStatus;

    public ProjectTerm() {
    }

    public ProjectTerm(AcademyYear academy_year, String academy_year_id, String description, String end_date, String id, String stage, List<StageTimeline> stage_timelines, String start_date) {
        this.academy_year = academy_year;
        this.academy_year_id = academy_year_id;
        this.description = description;
        this.end_date = end_date;
        this.id = id;
        this.stage = stage;
        this.stage_timelines = stage_timelines;
        this.start_date = start_date;
    }

    public Boolean getRegistered() {
        return isRegistered;
    }

    public void setRegistered(Boolean registered) {
        isRegistered = registered;
    }

    public String getRegisteredStatus() {
        return registeredStatus;
    }

    public void setRegisteredStatus(String registeredStatus) {
        this.registeredStatus = registeredStatus;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<Council> getCouncils() {
        return councils;
    }

    public void setCouncils(List<Council> councils) {
        this.councils = councils;
    }

    public List<Supervisor> getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(List<Supervisor> supervisors) {
        this.supervisors = supervisors;
    }

    public List<StageTimeline> getStage_timelines() {
        return stage_timelines;
    }

    public void setStage_timelines(List<StageTimeline> stage_timelines) {
        this.stage_timelines = stage_timelines;
    }

    public String getAcademy_year_id() {
        return academy_year_id;
    }

    public void setAcademy_year_id(String academy_year_id) {
        this.academy_year_id = academy_year_id;
    }

    public AcademyYear getAcademy_year() {
        return academy_year;
    }

    public void setAcademy_year(AcademyYear academy_year) {
        this.academy_year = academy_year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @NonNull
    @Override
    public String toString() {
        LocalDate startDate = LocalDate.parse(start_date);
        LocalDate endDate = LocalDate.parse(end_date);
        LocalDate now = LocalDate.now();
        if (!now.isBefore(startDate) && !now.isAfter(endDate)) {
            backgroundColor = R.drawable.bg_circle_completed;
            return "Đang diễn ra";
        } else if (now.isBefore(startDate)) {
            backgroundColor = R.drawable.bg_circle_pending;
            return "Chưa bắt đầu";
        } else {
            backgroundColor = R.drawable.bg_icon_success;
            return "Đã kết thúc";
        }
    }

    public String keySearch(){
        return "Đợt " + this.stage + " " + this.description + " " + this.academy_year.getYear_name();
    }
}
