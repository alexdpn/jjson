package com.ax.jjson.serializer;

public class Configuration {

    protected static Customer createCustomerInstance() {
        return new Customer(1L, "Alex", "alex@mymail.com");
    }

    protected static class Customer {
        private long id;
        private String name;
        private String email;

        public Customer() {
        }

        public Customer(long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    protected static class ClassWithNoFields {}
}
