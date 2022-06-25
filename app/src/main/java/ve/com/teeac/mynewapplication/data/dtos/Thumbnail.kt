package ve.com.teeac.mynewapplication.data.dtos

data class Thumbnail(
    val extension: String,
    val path: String
){
    fun getUrl(): String = "${this.path}/portrait_xlarge.${this.extension}"
}