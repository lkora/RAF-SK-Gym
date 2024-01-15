//
//  MainView.swift
//  GymTeam
//
//  Created by Luka Korica on 1/14/24.
//

import SwiftUI

struct MainView: View {
    @State private var selectedTab = 0
    @State var myUser: User
    
    var body: some View {
        TabView(selection: $selectedTab) {
            if myUser.userType == .client {
                AppointmentView()
                    .tabItem {
                        Image(systemName: "calendar")
                        Text("Appointments")
                    }
                    .tag(0)
                
            }
            
            if myUser.userType == .manager {
                GymManagerView(gym: Gym(name: "", description: "", numberOfTrainers: 2, trainings: generateDummyTrainings()))
            }
            
            if myUser.userType == .admin {
                UsersListView()
                    .tabItem {
                        Image(systemName: "person.3")
                        Text("Users")
                    }
                    .tag(2)

            }
            

            ProfileView(user: $myUser)
                .tabItem {
                    Image(systemName: "person")
                    Text("Profile")
                }
                .tag(10)
        }
    }
}

#Preview(UserType.client.name) {
    MainView(myUser: User(userType: .client, username: "pp", password: "somePwd", email: "petarpetrovic@raf.rs", dob: Date(), firstName: "Petar", lastName: "Petrovic"))
}
#Preview(UserType.manager.name) {
    MainView(myUser: User(userType: .manager, username: "mm", password: "somePwd", email: "markomarkovi@raf.rs", dob: Date(), firstName: "Marko", lastName: "Markovic"))
}
#Preview(UserType.admin.name) {
    MainView(myUser: User(userType: .admin, username: "aa", password: "somePwd", email: "adamadamovic@raf.rs", dob: Date(), firstName: "Adam", lastName: "Adamovic"))
}
