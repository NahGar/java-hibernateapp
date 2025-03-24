package org.ngarcia.hibernateapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Embeddable
public class Auditoria {

    @Column(name="creado_en")
    private LocalDateTime creadoEn;
    @Column(name="editado_en")
    private LocalDateTime editadoEn;

    @PrePersist
    public void prePersist() {
        System.out.println("Antes de persist");
        this.creadoEn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        System.out.println("Antes de update");
        this.editadoEn = LocalDateTime.now();
    }

    @PreRemove
    public void preRemove() {
        System.out.println("Antes de remove");
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public LocalDateTime getEditadoEn() {
        return editadoEn;
    }

    public void setEditadoEn(LocalDateTime editadoEn) {
        this.editadoEn = editadoEn;
    }
}
