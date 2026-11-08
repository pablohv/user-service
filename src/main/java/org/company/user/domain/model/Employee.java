package org.company.user.domain.model;

public record Employee(
        String firstName,
        String lastName,
        String email,
        String password,
        String phone,
        String state,
        String city,
        String cp,
        String address,
        String country,
        String nationality
) {

    public Employee withPassword(final String newPassword) {
        return new Employee(
                this.firstName, this.lastName, this.email,
                newPassword,
                this.phone, this.state, this.city, this.cp,
                this.address, this.country, this.nationality
        );
    }

}
