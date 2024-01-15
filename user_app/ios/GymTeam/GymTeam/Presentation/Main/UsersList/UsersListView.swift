//
//  UsersListView.swift
//  GymTeam
//
//  Created by Luka Korica on 1/15/24.
//

import SwiftUI

struct UsersListView: View {
    @State var users: [User] = [] // This should be your actual data

    var body: some View {
        NavigationStack {
            List {
                ForEach($users) { user in
                    UserRow(user: user)
                }
            }
            .navigationTitle("Users")
        }
    }
}

struct UserRow: View {
    @Binding var user: User

    var body: some View {
        VStack(alignment: .leading) {
            Text(user.username)
                .font(.headline)
            Text("User Type: \(user.userType.name)")
            Text("Email: \(user.email)")
            Text("Date of Birth: \(user.dob)")
            Button(user.isBanned ? "Unban" : "Ban") {
                // TODO: Ban API request
                user.isBanned.toggle()
            }
            .foregroundStyle(!user.isBanned ? .red : .accentColor)
        }
    }
}

func generateDummyUsers() -> [User] {
    var dummyUsers: [User] = []

    for i in 1...10 {
        let userType: UserType = i % 2 == 0 ? .client : .manager
        let username = "User\(i)"
        let password = "Password\(i)"
        let email = "user\(i)@example.com"
        let dob = Date()
        let firstName = "First\(i)"
        let lastName = "User\(i)"
        let isBanned = i % 2 == 0 ? true : false
        let memberCardNumber = i % 2 == 0 ? "\(i)" : nil
        let scheduledTrainings = i % 2 == 0 ? i : nil
        let gymName = i % 2 != 0 ? "Gym\(i)" : nil
        let employmentDate = i % 2 != 0 ? Date() : nil
        let user = User(userType: userType, username: username, password: password, email: email, dob: dob, firstName: firstName, lastName: lastName, isBanned: isBanned, memberCardNumber: memberCardNumber, scheduledTrainings: scheduledTrainings, gymName: gymName, employmentDate: employmentDate)
        dummyUsers.append(user)
    }

    return dummyUsers
}


#Preview {
    UsersListView(users: generateDummyUsers())
}
