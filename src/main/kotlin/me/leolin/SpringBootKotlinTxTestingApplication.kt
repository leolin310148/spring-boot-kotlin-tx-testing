package me.leolin

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.Transactional
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
open class SpringBootKotlinTxTestingApplication

fun main(args: Array<String>) {
    val context = SpringApplication.run(SpringBootKotlinTxTestingApplication::class.java, *args)

    val kotlinService = context.getBean(KotlinRecordService::class.java)

    val kId = kotlinService.save("foo")
    kotlinService.update(kId, "bar")
    kotlinService.update2(kId, "bar2")
    val kRecord = kotlinService.get(kId)
    println(kRecord.name)

    val javaService = context.getBean(JavaRecordService::class.java)
    val jId = javaService.save("foo")
    javaService.update(jId, "bar")
    javaService.update2(jId, "bar2")
    val jRecord = javaService.get(jId)
    println(jRecord.name)
}

@Entity
class KotlinRecord(
        @Id @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
        var id: Long = 0,
        var name: String = ""
)

interface KotlinRecordDao : JpaRepository<KotlinRecord, Long>

@Service open class KotlinRecordService {

    @Autowired lateinit var kotlinRecordDao: KotlinRecordDao

    @Transactional
    open fun save(name: String): Long {
        return kotlinRecordDao.save(KotlinRecord(name = name)).id
    }

    @Transactional
    open fun update(id: Long, name: String) {
        println("update")
        kotlinRecordDao.findOne(id).apply {
            this.name = name
            kotlinRecordDao.save(this)
        }
    }

    @Transactional
    open fun update2(id: Long, name: String) {
        println("update2")
        kotlinRecordDao.findOne(id).apply {
            this.name = name
        }
    }

    open fun get(id: Long) = kotlinRecordDao.findOne(id)
}