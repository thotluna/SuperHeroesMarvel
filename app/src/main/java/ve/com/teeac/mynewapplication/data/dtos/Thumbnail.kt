package ve.com.teeac.mynewapplication.data.dtos

data class Thumbnail(
    val extension: String?,
    val path: String?
){
    val url: String? = if(extension != null || path != null){
        "${this.path}/portrait_xlarge.${this.extension}"
    }else{
        null
    }
}