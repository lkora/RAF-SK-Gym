//
//  LoginView.swift
//  GymTeam
//
//  Created by Luka Korica on 1/13/24.
//

import SwiftUI

struct LoginView: View {
    
    @State private var username: String = ""
    @State private var password: String = ""
    @State private var serverURL: String = "" {
        didSet {
            ServerSettings.updateBaseServerUrl(newState: serverURL)
        }
    }

    var body: some View {
        NavigationStack {
            
            VStack(alignment: .leading) {
                Spacer()

                HStack {
                    Text("Server address:")
                    Text(serverURL)
                        .padding()
                    Spacer()
                }
                NavigationLink(destination: SetBaseServerUrlView(serverUrl: $serverURL),
                               label: { Text("Change") })

                
                Spacer()
                TextField("Username", text: $username)
                    .padding()
                    .border(Color.gray, width: 0.5)
                
                SecureField("Password", text: $password)
                    .padding()
                    .border(Color.gray, width: 0.5)

                HStack(spacing: 50) {
                    Button(action: {
                        // TODO: Add login request, navigate to main app
                    }) {
                        Text("Login")
                    }
                    
                    NavigationLink(destination: RegisterView(),
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
            serverURL = ServerSettings.baseServerUrl ?? serverURL
        })
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
