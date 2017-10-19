import com.amazonaws.services.glacier.model.DescribeJobRequest
import com.amazonaws.services.glacier.model.GetJobOutputRequest
import com.amazonaws.services.glacier.model.InitiateJobRequest
import com.amazonaws.services.glacier.model.JobParameters
import java.io.File

/**
 * <p>
 * Created by alexei.vylegzhanin@gmail.com on 10/17/2017.
 */

fun main(args: Array<String>) {
    val vaultName = args.getOrNull(0) ?: run {
        println("Usage: Inventory_retrieval_jobKt <vaultName> [jobId]")
        return
    }
    val jobId = args.getOrNull(1)

    if (jobId == null) {
        glacier.initiateJob(InitiateJobRequest(vaultName, JobParameters().withType("inventory-retrieval"))).let {
            println("jobId = " + it.jobId)
        }
    } else {
        glacier.describeJob(DescribeJobRequest(vaultName, jobId)).apply {
            println("""
                isCompleted = $isCompleted
                statusMessage = $statusMessage
                """.trimIndent())

        }.let {
            if (it.isCompleted) {
                glacier.getJobOutput(GetJobOutputRequest().withVaultName(vaultName).withJobId(jobId)).apply {
                    File("$vaultName.json").outputStream().use {
                        body.copyTo(it)
                    }
                }
            }
        }
    }
}
