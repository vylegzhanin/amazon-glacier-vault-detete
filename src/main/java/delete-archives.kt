
import com.amazonaws.services.glacier.model.DeleteArchiveRequest
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import java.io.File

/**
 * <p>
 * Created by alexei.vylegzhanin@gmail.com on 10/18/2017.
 */

fun main(args: Array<String>) {
    val vaultName = args.getOrNull(0) ?: run {
        println("Usage: Delete_archivesKt <vaultName>")
        return
    }

    val mapper = ObjectMapper()
    val factory = mapper.factory
    val jsonParser = factory.createParser(File("$vaultName.json").readText())
    val jsonNode: JsonNode = mapper.readTree(jsonParser)
    val archiveList = jsonNode["ArchiveList"] as ArrayNode
    archiveList.forEach {
        val archiveId = it["ArchiveId"].asText()
        println(archiveId)
        glacier.deleteArchive(DeleteArchiveRequest(vaultName, archiveId))
    }
}