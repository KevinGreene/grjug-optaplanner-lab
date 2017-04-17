package com.surrealanalysis.totalitaria

import com.github.javafaker.Faker
import com.surrealanalysis.totalitaria.domain.*
import java.util.*
import kotlin.collections.LinkedHashSet

data class ScheduleGenerator(val employeeCount: Int,
                             val projectCount: Int,
                             val skillCount: Int,
                             val tasksPerProject: Int,
                             val timeGrainInMinutes: Int,
                             val minutesInDay: Int,
                             val dayCount: Int) {

    val faker: Faker = Faker()
    val random: Random = Random()

    fun getName() = "${faker.name().firstName()} ${faker.name().lastName()}"
    fun getCompanyName() = faker.company().name()!!
    fun getSkillName() = faker.resolve("job.key_skills")!!

    fun <T> getRandomSubset(items: List<T>, maxCount: Int): List<T> {
        val count = random.nextInt(maxCount + 1)
        val indices = (1..count).map { random.nextInt(items.size) }
        return indices.map { items.get(it) }.distinct()
    }

    fun generate(): Schedule {
        val skills: List<Skill> = (1..skillCount).map { Skill(it, getSkillName()) }.distinct()

        val employees: List<Employee> = (1..employeeCount).map {
            Employee(it, getName(), getRandomSubset(skills, 3).toSet())
        }

        val projects: List<Project> = (1..projectCount).map {
            Project(it, getCompanyName())
        }

        val projectTasks: List<ProjectTask> = projects.mapIndexed { index, project ->
            (1..tasksPerProject).map {
                val id = it + index * tasksPerProject
                ProjectTask(id, project, random.nextInt(8) + 2, LinkedHashSet(getRandomSubset(skills, 2)))
            }
        }.flatten()

        val days: List<Day> = (1..dayCount).map(::Day)
        val timeGrainsPerDay = minutesInDay / timeGrainInMinutes
        val timeGrains: List<TimeGrain> = days.mapIndexed { dayIndex, day ->
            val dayBuffer = dayIndex * timeGrainsPerDay
            (1..timeGrainsPerDay).map {
                TimeGrain(it + dayBuffer, day)
            }
        }.flatten()

        val assignments: List<EmployeeShiftTaskAssignment> = employees.mapIndexed { employeeIndex, employee ->
            timeGrains.mapIndexed { index, timeGrain ->
                val id = employeeIndex * timeGrains.size + index
                EmployeeShiftTaskAssignment(id, employee, timeGrain)
            }
        }.flatten()

        return Schedule(projects, employees, skills, days, timeGrains, projectTasks, assignments)
    }
}