set java.runtime.version=1.8
set PORT=443
set hibernate.connection.url=jdbc:postgresql://localhost:5432/Articles_Hub?user=postgres\&password=root
set hibernate.connection.username=postgres
set hibernate.connection.password=root
echo set hibernate.connection.url="jdbc:postgresql://ec2-54-163-254-143.compute-1.amazonaws.com:5432/deafbm84fi4v4?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory&user=yoljebldhzyeag&password=20a93f9d45969c3723a06d2f2ba6f8613be85ce4809f665ec03dae89b97a72e6"
java -cp target/classes;target/dependency/* com.articles_hub.heroku.Main