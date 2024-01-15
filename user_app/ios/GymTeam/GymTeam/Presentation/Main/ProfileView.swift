//
//  ProfileView.swift
//  GymTeam
//
//  Created by Luka Korica on 1/14/24.
//

import SwiftUI

struct ProfileView: View {
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>

    @Binding var user: User
    
    var body: some View {
        NavigationStack {
            Form {
                Text("User Type:  \(user.userType.name)")
                TextField("Username", text: $user.username)
                SecureField("Password", text: $user.password)
                TextField("Email", text: $user.email)
                DatePicker("Date of Birth", selection: $user.dob, displayedComponents: .date)
                TextField("First Name", text: $user.firstName)
                TextField("Last Name", text: $user.lastName)
                
                if user.userType == .client {
                    Text("Member Card Number: \(user.memberCardNumber ?? "N/A")")
                    Text("Scheduled Trainings: \(user.scheduledTrainings ?? 0)")
                }
                
                if user.userType == .manager {
                    TextField("Gym Name", text: Binding(
                        get: { self.user.gymName ?? "" },
                        set: { self.user.gymName = $0 }
                    ))
                    Text("Employment Date: \(user.employmentDate?.description ?? "N/A")")
                }
                
                Button("Update Profile") {
                    // TODO: Update user profile API
                    self.presentationMode.wrappedValue.dismiss()
                }
            }
            .navigationBarTitle("Profile")
        }
    }
}

#Preview {
    ProfileView(user: .constant(User(userType: .client, username: "", password: "", email: "", dob: Date(), firstName: "", lastName: "", memberCardNumber: nil, scheduledTrainings: nil, gymName: nil, employmentDate: nil)))
}
