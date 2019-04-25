package spring_h2_playground;

public class PlaygroundConfig {

    public static abstract class Datasource {

        private String url;
        private String user;
        private String password;
        private String driverClassName;
        private String auto;
        private String dialect;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public String getAuto() {
            return auto;
        }

        public void setAuto(String auto) {
            this.auto = auto;
        }

        public String getDialect() {
            return dialect;
        }

        public void setDialect(String dialect) {
            this.dialect = dialect;
        }

    }

    //@ConfigurationProperties("spring.datasource1")
    public static class Datasource1 extends Datasource {

    }

    //@ConfigurationProperties("spring.datasource2")
    public static class Datasource2 extends Datasource {

    }

}
