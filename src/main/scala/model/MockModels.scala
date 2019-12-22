package com.datatech.restapi

object MockModels {
  case class PersonRow (
                     name: String,
                     age: Int
                     )
  case class Person(name: String, age: Int)
       extends ModelBase[Person,PersonRow] {
    def to: Person => PersonRow = p => PersonRow (
      name = p.name,
      age = p.age
    )
    def from: PersonRow => Person = row => Person(
      name = row.name,
      age = row.age
    )
  }

  case class AddressRow (
                       province: String,
                       city: String,
                       street: String,
                       zip: String
                     )
  case class Address(
                      province: String,
                      city: String,
                      street: String,
                      zip: String
                    )
    extends ModelBase[Address,AddressRow] {
    def to: Address => AddressRow = addr => AddressRow (
      province = addr.province,
      city = addr.city,
      street = addr.street,
      zip = addr.zip
    )
    def from: AddressRow => Address = row => Address(
      province = row.province,
      city = row.city,
      street = row.street,
      zip = row.zip
    )
  }

}
