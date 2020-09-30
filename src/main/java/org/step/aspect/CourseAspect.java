package org.step.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.step.entities.Course;

@Component
@Aspect
@Order(-1)
public class CourseAspect {

    @Pointcut("execution(public org.step.entities.Course org.step.services.CrudService.find(Long))")
    public void findMethodsOfCourseCrudServices() {}

    @AfterReturning(
            pointcut = "findMethodsOfCourseCrudServices()",
            returning = "course"
    )
    public void resultOfFindBeforehand(JoinPoint joinPoint, Course course) {
        // Получить возвращаемое значение и вывести его на экран;
        System.out.println("---------------------------------------------------------------------");

        System.out.printf("After returning %s is called%n", joinPoint.getSignature().getName());

        System.out.println(course.toString());

        System.out.println("End of after returning");
        System.out.println("---------------------------------------------------------------------");
    }

    @AfterReturning(
            pointcut = "findMethodsOfCourseCrudServices()",
            returning = "course"
    )
    public void alteringReturningValue(JoinPoint joinPoint, Course course) {
        // Подменить возвращаемое значение.
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("After returning %s is called%n", joinPoint.getSignature().getName());

        System.out.println("Before changes: " + course.toString());
        course.setTopic("Spring Data");
        System.out.println("After changes: " + course.toString());

        System.out.println("End of after returning");
        System.out.println("---------------------------------------------------------------------");
    }
}
