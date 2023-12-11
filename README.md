# Kafka2Consumer

https://www.youtube.com/watch?v=m3GAHcN9aoA&t=7705s

Yukarıdaki videoyu izleyerek kafka ile 1 prosuder 2 consumer yaratıp kafkanın çalışma prensiblerini anlamayı hedefledim. 
Prosuderdan user oluşturuyoruz user(Postgres) tablosuna kaydediyor ve  consumerlara userı(addres ve maill bilgileri) yolluyoruz.
Address servisinde adresini payloddan alıp  address(Postgres) tablosuba kaydediyoruz.  Notification servisinde ise mailli kısmını 
Couschbase bucketine verileri json şekilde kaydediyor.

### Tech Stack
- Java 17
- Spring Boot
- Lombok
- Apache Kafka
- Postgresql
- Couchbase
- Kafka UI
- Docker


# Others
- Bu projede dockerı ilk defa deneyimledim. Docker desktop windows için indirdim. Sonrasında setup altındaki docker-compose.yml çalıştırmak yeterli oldu.
- Postgresql ile ilgili olarak docker dışında defult port olarak 5432 kullanıyorsanız dockerdan dışarıya görüntülemek için portunuzu değiştirebilirsiniz.(Görünütüleme için pgAdmin kullandım)
- Couchbase için ayağa kaldırdıktan sonra localde 8091 portundan arayüzünü açıp user oluşturmak gerekiyor. Sonrasında bucket oluşturuyoruz. Couchbase ayarlarını koddan set ediyoruz kullanmak için.
- Kafka UI topicleri consumerları görüntüleyip yönetebiliyoruz. Topicimizin partinını değiştirdiğimizde kafka yeniden balance yapmaya çalışır. Topiciğin nekadar saklanacığı nekadarlık veriyi depolayacğını ayarlayabiliyoruz. 
-  Kafka paralel veri işleme yeteneğine sahip olur ama bizde projerelimizi çoklamalıyız yoksa aynı proje  tüm partiationları dinlerse çok anlamlı olmaz. Örnek olarak 4 partion ayarlarsak 2tane consumer uygulaması çalıştırırsak 0-1 i birisi 2-3  diğer uygulamayı dinler. Servise sürekli istek geldiğinde partionlarrı 1-2-3-4 diye gider açık olan uygulamamız hangisini dinliyorsa istekler oralar düşer.
-  Partion sayısını değiştirince uygulamarımızı rebalanse eder 2 ye 3 şekilde partionları dağtır. Her pertionı yalnızca 1 consumer atayabilir. Aynı consumer grup IDliler topic partionunu bölüşür. Yani 1 den fazla consumer 1 partiona gidememktedir. Partion sayısından fazla aynı consumer grup ID'li  consumer olduğunda fazla olan consumerlar idle statede bekler.
- Kafka için temelde bi configurationda birtane bean oluşturup bu beanı kullanacağımızda inject edip method üzerinden datayı prdouce consume ettik.
