//
//  MainView.swift
//  GymTeam
//
//  Created by Luka Korica on 1/14/24.
//

import SwiftUI

struct MainView: View {
    @ObservedObject private var viewModel: MainViewModel
    @State private var selectedTab: Int
    

    init(selectedTab: Int = 0, viewModel: MainViewModel) {
        self.selectedTab = selectedTab
        self.viewModel = viewModel
    }
    
    var body: some View {
        TabView(selection: $selectedTab) {
            if viewModel.myUser.userType == .client {
                AppointmentView()
                    .tabItem {
                        Image(systemName: "calendar")
                        Text("Appointments")
                    }
                    .tag(0)
                
            }
            
            if viewModel.myUser.userType == .manager {
                GymManagerView(gym: Gym(id: 0, name: "", description: "", numberOfTrainers: 2, trainings: generateDummyTrainings()))
            }
            
            if viewModel.myUser.userType == .admin {
                UsersListView()
                    .tabItem {
                        Image(systemName: "person.3")
                        Text("Users")
                    }
                    .tag(2)

            }
            

            ProfileView(user: $viewModel.myUser)
                .tabItem {
                    Image(systemName: "person")
                    Text("Profile")
                }
                .tag(10)
        }
        .onAppear {
            viewModel.loadMyUser()
        }
    }
}


#Preview(UserType.client.name) {
    let mainViewModelMockup: MainViewModel = MainViewModel(apiService: AlamofireAPIService(baseUrlString: ""), myUser: User(userType: .client, username: "pp", password: "somePwd", email: "petarpetrovic@raf.rs", birthDate: Date(), firstName: "Petar", lastName: "Petrovic"))
    return MainView(viewModel: mainViewModelMockup)
}
#Preview(UserType.manager.name) {
    let mainViewModelMockup: MainViewModel = MainViewModel(apiService: AlamofireAPIService(baseUrlString: ""), myUser: User(userType: .manager, username: "mm", password: "somePwd", email: "markomarkovi@raf.rs", birthDate: Date(), firstName: "Marko", lastName: "Markovic"))
    return MainView(viewModel: mainViewModelMockup)
}
#Preview(UserType.admin.name) {
    let mainViewModelMockup: MainViewModel = MainViewModel(apiService: AlamofireAPIService(baseUrlString: ""), myUser: User(userType: .admin, username: "aa", password: "somePwd", email: "adamadamovic@raf.rs", birthDate: Date(), firstName: "Adam", lastName: "Adamovic"))
    return MainView(viewModel: mainViewModelMockup)
}
