class AppRepository {
    companion object {
        private var INSTANCE: AppRepository? = null

        fun getInstance(): AppRepository {
            if (INSTANCE == null) {
                INSTANCE = AppRepository()
            }
            return INSTANCE
                ?: throw IllegalStateException("Репозиторий не инициализирован")
        }
    }

    var listOfFaculty: MutableLiveData<ListOfFaculty?> = MutableLiveData()
    var faculty: MutableLiveData<Faculty> = MutableLiveData()

    var listOfGroup: MutableLiveData<ListOfGroup?> = MutableLiveData()
    var group: MutableLiveData<Group> = MutableLiveData()

    var listOfStudent: MutableLiveData<ListOfStudent?> = MutableLiveData()
    var student: MutableLiveData<Student> = MutableLiveData()

    fun addFaculty(faculty: Faculty) {
        val listTmp = (listOfFaculty.value ?: ListOfFaculty()).apply {
            item.add(faculty)
        }

        listOfFaculty.postValue(listTmp)
        setCurrentFaculty(faculty)
    }

    fun getFacultyPosition(faculty: Faculty): Int = listOfFaculty.value?.items?.indexOfFirst {
        it.id == faculty.id} ?: -1

    fun getFacultyPosition() = getFacultyPosition(faculty.value ?: Faculty())

    fun setCurrentFaculty(position: Int) {
        if (listOfFaculty.value == null || position < 0 ||
            (listOfFaculty.value?.items?.size!!<=position))
            return
        setCurrentFaculty(listOfFaculty.value?.items!![position])
    }

    fun setCurrentFaculty(_faculty: Faculty) {
        faculty.postValue(_faculty)
    }

    fun updateFaculty(faculty: Faculty) {
        val position = getFacultyPosition(faculty)
        if (position < 0) addFaculty(faculty)
        else {
            val listTmp = listOfFaculty.value!!
            listTmp.items[position] = faculty
            listOfFaculty.postValue(listTmp)
        }
    }

    fun deleteFaculty(faculty: Faculty) {
        va; listTmp = listOfFaculty..value!!
        if (listTmp.items.remove(faculty)) {
            listOfFaculty.postValue(listTmp)
        }
        setCurrentFaculty(0)
    }
}