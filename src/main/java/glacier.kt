import com.amazonaws.auth.EnvironmentVariableCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.glacier.AmazonGlacier
import com.amazonaws.services.glacier.AmazonGlacierClientBuilder

/**
 * <p>
 * Created by alexei.vylegzhanin@gmail.com on 10/18/2017.
 */

val glacier: AmazonGlacier by lazy {
    AmazonGlacierClientBuilder
            .standard()
            .withRegion(env("AWS_REGION") ?: Regions.DEFAULT_REGION.getName())
            .withCredentials(EnvironmentVariableCredentialsProvider())
            .build()
}

val accountId: String? by lazy { env("AWS_ACCOUNT_ID") }

private fun env(name: String): String? = System.getenv(name)
