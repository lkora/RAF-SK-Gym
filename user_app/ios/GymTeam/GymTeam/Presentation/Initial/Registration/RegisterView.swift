//
//  RegisterView.swift
//  GymTeam
//
//  Created by Luka Korica on 1/14/24.
//

import SwiftUI

struct RegisterView: View {
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @ObservedObject var registrationViewModel: RegistrationViewModel

    var body: some View {
        
        NavigationStack {
            Form {
                Picker("User Type", selection: $registrationViewModel.user.userType) {
                    Text(UserType.client.name).tag(UserType.client)
                    Text(UserType.manager.name).tag(UserType.manager)
                }
                
                TextField("Username", text: $registrationViewModel.user.username)
                SecureField("Password", text: $registrationViewModel.user.password)
                TextField("Email", text: $registrationViewModel.user.email)
                DatePicker("Date of Birth", selection: $registrationViewModel.user.dob, displayedComponents: .date)
                TextField("First Name", text: $registrationViewModel.user.firstName)
                TextField("Last Name", text: $registrationViewModel.user.lastName)
                
                if registrationViewModel.user.userType == .manager {
                    TextField("Gym Name", text: Binding(
                        get: { registrationViewModel.user.gymName ?? "" },
                        set: { registrationViewModel.user.gymName = $0 }
                    ))
                    DatePicker("Employment Date", selection: Binding(
                        get: { registrationViewModel.user.employmentDate ?? Date() },
                        set: { registrationViewModel.user.employmentDate = $0 }
                    ), displayedComponents: .date)
                }
                
                Button("Register") {
                    registrationViewModel.register()
                }
            }
            .navigationBarTitle("Registration")
        }
    }
}

