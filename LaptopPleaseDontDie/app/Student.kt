package com.example.list_temp.data

data class Student (
    val id: UUID = UUID.randomUUID(),
    var lastName: String = "",
    var firstName: String = "",
    var middleName: String = "",
    var birthDate: Data = Data(),
    var groupID: UUID? = null,
    var phone: String = "",
    var sex: Int = 0
) {
    val shortName
        get() = lastName +
                (if(firstName.length > 0) {" ${firstName[0]}."} else "") +
                (if(middleName.length > 0) {" ${middleName[0]}."} else "")
}