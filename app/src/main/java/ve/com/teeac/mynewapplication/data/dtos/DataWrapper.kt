package ve.com.teeac.mynewapplication.data.dtos

open class DataWrapper<T>(
    val code: Int = 200,
    val status: String = "success",
    val `data`: DataContainer<T>
){
    fun copy(data: DataContainer<T> = this.data) = DataWrapper(200, "", data)
}
