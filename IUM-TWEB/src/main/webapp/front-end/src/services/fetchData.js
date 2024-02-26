import {store} from "@/main";

export async function getAllCourses() {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=courses&choice=getAllCourses', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token
        }
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function getAllActiveCourses(data) {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=courses&choice='+data, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function login(user, pass) {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=user&choice=login&username='+user+'&password='+pass, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            username: user,
            password: pass
        })
    })
    if (!response.ok) {
      throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function signUp(user, pass, name, gender) {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=user&choice=register&username='+user+'&password='+pass+'&name='+name+'&gender='+gender, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            username: user,
            password: pass,
            name: name,
            gender: gender
        })
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function logout() {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=user&choice=logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token},
        body: JSON.stringify({
        })
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function getAllTeachers() {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=teacher&choice=getAllTeachers', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token
        }
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function getAllActiveTeachers() {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=teacher&choice=getAllActiveTeachers', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token
        }
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function getQualifiedTeachers(title) {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=lesson&choice=teachersAvailable&title='+title, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token
        }
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function getSlots(teacherName, teacherSurname, date) {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=lesson&choice=timeSlots&name='+teacherName+'&surname='+teacherSurname+'&date='+date, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token
        }
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function bookLesson(course, teacherName, teacherSurname, user, date, hour) {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=lesson&choice=insertLesson&title='+course+'&name='+teacherName+'&surname='+teacherSurname+'&username='+user+'+&date='+date+'&hour='+hour, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token
        },
        body: JSON.stringify({
            choice: 'insertLesson',
            title: course,
            name: teacherName,
            surname: teacherSurname,
            username: user,
            date: date,
            hour: hour
        })
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function getLessons(username, status) {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=lesson&choice=userLessons&username='+username+'&status='+status, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token
        }
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function getAllLessons() {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=lesson&choice=allLessons', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token
        }
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function setDone(course, teacherName, teacherSurname, user, date, hour) {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=lesson&choice=setAccomplished&title='+course+'&name='+teacherName+'&surname='+teacherSurname+'&username='+user+'+&date='+date+'&hour='+hour, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token
        },
        body: JSON.stringify({
            choice: 'setAccomplished',
            title: course,
            name: teacherName,
            surname: teacherSurname,
            username: user,
            date: date,
            hour: hour
        })
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function setDeleted(course, teacherName, teacherSurname, user, date, hour) {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=lesson&choice=deleteLesson&title='+course+'&name='+teacherName+'&surname='+teacherSurname+'&username='+user+'+&date='+date+'&hour='+hour, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token
        },
        body: JSON.stringify({
            choice: 'deleteLesson',
            title: course,
            name: teacherName,
            surname: teacherSurname,
            username: user,
            date: date,
            hour: hour
        })
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function setTeacherDisabled(teacherName, teacherSurname) {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=teacher&choice=delete&name='+teacherName+'&surname='+teacherSurname, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token
        },
        body: JSON.stringify({
            name: teacherName,
            surname: teacherSurname
        })
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function addTeacher(teacherName, teacherSurname) {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=teacher&choice=add&name='+teacherName+'&surname='+teacherSurname, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token
        },
        body: JSON.stringify({
            name: teacherName,
            surname: teacherSurname
        })
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function setCourseDisabled(title) {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=courses&choice=remove&title='+title, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token
        },
        body: JSON.stringify({
            title: title
        })
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function addCourse(title, description) {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=courses&choice=add&title='+title+'&desc='+description, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token
        },
        body: JSON.stringify({
            title: title,
            desc: description
        })
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function getAllTeachings() {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=teaching&choice=allTeachings', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token
        }
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function addTeaching(course, teacherName, teacherSurname) {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=teaching&choice=insertTeaching&name='+teacherName+'&surname='+teacherSurname+'&title='+course, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token
        },
        body: JSON.stringify({
            name: teacherName,
            surname: teacherSurname,
            title: course
        })
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}

export async function setTeachingDisabled(course, teacherName, teacherSurname) {
    const response = await fetch('http://localhost:8080/IUM_TWEB_war_exploded/ServletController?action=teaching&choice=deleteTeaching&name='+teacherName+'&surname='+teacherSurname+'&title='+course, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': store.data.token
        },
        body: JSON.stringify({
            name: teacherName,
            surname: teacherSurname,
            title: course
        })
    })
    if (!response.ok) {
        throw new Error("Invalid response from server");
    }
    return await response.json();
}
