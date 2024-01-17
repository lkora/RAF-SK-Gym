//
//  LoginView.swift
//  GymTeam
//
//  Created by Luka Korica on 1/13/24.
//

import SwiftUI

struct LoginView: View {
    
    @ObservedObject private var loginViewModel: LoginViewModel = LoginViewModel()
    
    var body: some View {
        NavigationStack {

            VStack(alignment: .leading) {
                Spacer()
                
                HStack {
                    Text("Server address:")
                    Text(loginViewModel.serverURL)
                        .padding()
                    Spacer()
                }
                NavigationLink(destination: SetBaseServerUrlView(serverUrl: $loginViewModel.serverURL),
                               label: { Text("Change") })
                
                
                Spacer()
                TextField("Username", text: $loginViewModel.username)
                    .padding()
                    .border(Color.gray, width: 0.5)
                
                SecureField("Password", text: $loginViewModel.password)
                    .padding()
                    .border(Color.gray, width: 0.5)
                
                HStack(spacing: 50) {
                    NavigationLink(destination: MainView(viewModel: MainViewModel(apiService: loginViewModel.apiService)), isActive: $loginViewModel.isLoggedIn) {
                        Button(action: {
                            loginViewModel.login()
                        }) {
                            Text("Login")
                        }
                    }
                    
                    
                    NavigationLink(destination: RegisterView(registrationViewModel: RegistrationViewModel(apiService: loginViewModel.apiService)),
                                   label: { Text("Register") })
                }
                .padding()
                
                
                Spacer()
                Spacer()
                
                
            }
            .padding(.horizontal, 40)
            .navigationTitle("Login")
        }
        
        
        .onAppear(perform: {
            loginViewModel.serverURL = ServerSettings.baseServerUrl ?? loginViewModel.serverURL
        })
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
