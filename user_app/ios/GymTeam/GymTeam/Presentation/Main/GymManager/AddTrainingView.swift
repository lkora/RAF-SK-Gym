//
//  AddTrainingView.swift
//  GymTeam
//
//  Created by Luka Korica on 1/15/24.
//

import SwiftUI

struct AddTrainingView: View {
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @State private var training: Training
    
    init() {
        _training = .init(initialValue: Training(name: "", description: "", numberOfTrainers: 0, price: 0.0, isGroup: false, maxParticipants: nil))
    }
    
    var body: some View {
        NavigationStack {
            Form(content: {
                TextField("Name", text: $training.name)
                TextField("Description", text: $training.description)
                Stepper(value: $training.numberOfTrainers, in: 0...20) {
                    Text("Number of Trainers: \(training.numberOfTrainers)")
                }
                TextField("Price:", value: $training.maxParticipants, formatter: NumberFormatter())

                Toggle(isOn: $training.isGroup) {
                    Text("Is Group Training?")
                }
                if training.isGroup {
                    TextField("Max Participants", value: $training.maxParticipants, formatter: NumberFormatter())
                }
                Button("Save new training") {
                    // TODO: Save new training API
                    self.presentationMode.wrappedValue.dismiss()
                }
                
            })
            .navigationTitle("New Training")
            
        }
    }
}

#Preview {
    AddTrainingView()
}
