//
//  RegisterView.swift
//  GymTeam
//
//  Created by Luka Korica on 1/14/24.
//

import SwiftUI

struct RegisterView: View {
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    @State private var user = User(userType: .client, username: "", password: "", email: "", dob: Date(), firstName: "", lastName: "", memberCardNumber: nil, scheduledTrainings: nil, gymName: nil, employmentDate: nil)

    var body: some View {
        
        NavigationStack {
            Form {
                Picker("User Type", selection: $user.userType) {
                    Text(UserType.client.name).tag(UserType.client)
                    Text(UserType.manager.name).tag(UserType.manager)
                }
                
                TextField("Username", text: $user.username)
                SecureField("Password", text: $user.password)
                TextField("Email", text: $user.email)
                DatePicker("Date of Birth", selection: $user.dob, displayedComponents: .date)
                TextField("First Name", text: $user.firstName)
                TextField("Last Name", text: $user.lastName)
                
                if user.userType == .manager {
                    TextField("Gym Name", text: Binding(
                        get: { self.user.gymName ?? "" },
                        set: { self.user.gymName = $0 }
                    ))
                    DatePicker("Employment Date", selection: Binding(
                        get: { self.user.employmentDate ?? Date() },
                        set: { self.user.employmentDate = $0 }
                    ), displayedComponents: .date)
                }
                
                Button("Register") {
                    // TODO: Registration and navigatie to login
                    self.presentationMode.wrappedValue.dismiss()
                }
            }
            .navigationBarTitle("Registration")
        }
    }
}

#Preview {
    RegisterView()
}
