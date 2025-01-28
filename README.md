GESTION SCOLAIRE AVEC JAVAFX, JPA ET POSTGRESQL
Créer un fichier nommé persistence.xml dans src/main/resources/META-INF/ et mettez le contenu suivant.

<persistence
        xmlns="http://xmlns.jcp.org/xml/ns/persistence"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
             http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd"
        version="2.1">
    <persistence-unit name="L3GL">

        <properties>
            <property name="jakarta.persistence.jdbc.driver" value="org.postgresql.Driver" /> <!-- DB Driver -->
            <!--<property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost/db_name" />-->
            <property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/db_name" />
            <!-- BD Mane -->
            <property name="jakarta.persistence.jdbc.user" value="db_username" /> 
            <property name="jakarta.persistence.jdbc.password" value="db_password" /> 
            <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/> <!-- DB Dialect -->
            <property name="hibernate.hbm2ddl.auto" value="update" /> <!-- create / create-drop / update -->

            <property name="hibernate.show_sql" value="false" /> <!-- Show SQL in console -->
            <property name="hibernate.format_sql" value="true" /> <!-- Show SQL formatted -->
        </properties>
    </persistence-unit>
</persistence>
