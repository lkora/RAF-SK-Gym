//
//  ProfileView.swift
//  GymTeam
//
//  Created by Luka Korica on 1/14/24.
//

import SwiftUI

struct ProfileView: View {
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    @ObservedObject var viewModel: ProfileViewModel

    var body: some View {
        NavigationStack {
            Form {
                Text("User Type:  \(viewModel.myUser.userType.name)")
                TextField("Username", text: $viewModel.myUser.username)
                SecureField("Password", text: $viewModel.myUser.password)
                TextField("Email", text: $viewModel.myUser.email)
                DatePicker("Date of Birth", selection: $viewModel.myUser.birthDate, displayedComponents: .date)
                TextField("First Name", text: $viewModel.myUser.firstName)
                TextField("Last Name", text: $viewModel.myUser.lastName)
                
                if viewModel.myUser.userType == .client {
                    Text("Member Card Number: \(viewModel.myUser.memberCardNumber ?? "N/A")")
                    Text("Scheduled Trainings: \(viewModel.myUser.scheduledTrainings ?? 0)")
                }
                
                if viewModel.myUser.userType == .manager {
                    TextField("Gym Name", text: Binding(
                        get: { self.viewModel.myUser.gymName ?? "" },
                        set: { self.viewModel.myUser.gymName = $0 }
                    ))
                    Text("Employment Date: \(viewModel.myUser.employmentDate?.description ?? "N/A")")
                }
                Button(action: { viewModel.saveProfile() }, label: {
                    Text("Save profile")
                })
                
            }
            .navigationBarTitle("Profile")
        }
    }
}

#Preview {
    ProfileView(viewModel: ProfileViewModel(apiService: AlamofireAPIService(baseUrlString: ""), myUser: User(userType: .client, username: "", password: "", email: "", birthDate: Date(), firstName: "", lastName: "", memberCardNumber: nil, scheduledTrainings: nil, gymName: nil, employmentDate: nil)))
}
