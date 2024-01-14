//
//  SetBaseServerUrlView.swift
//  GymTeam
//
//  Created by Luka Korica on 1/14/24.
//

import SwiftUI

struct SetBaseServerUrlView: View {
    @Binding var serverUrl: String
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    var body: some View {
        NavigationStack {
            VStack {
                Text("Server URL")
                    .font(.title)
                TextField("Server URL", text: $serverUrl)
                    .padding()
                    .border(Color.gray, width: 0.5)
                
                Button("Save") {
                    ServerSettings.updateBaseServerUrl(newState: serverUrl)
                    self.presentationMode.wrappedValue.dismiss()
                }
                
                .padding()
            }
            
            .padding(.horizontal, 40)
            .navigationTitle("SetBaseServerUrl")
        }
    }
}

#Preview {
    SetBaseServerUrlView(serverUrl: .constant("192.168.0.34"))
}
