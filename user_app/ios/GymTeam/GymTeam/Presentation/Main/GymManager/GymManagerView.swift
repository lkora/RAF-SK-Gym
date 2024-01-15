//
//  GymManagerView.swift
//  GymTeam
//
//  Created by Luka Korica on 1/15/24.
//

import SwiftUI

struct GymManagerView: View {
    @State var gym: Gym
    @State var appointments: [Appointment] = []

    var body: some View {
        NavigationStack {
            Form {
                Section(header: Text("Gym Details")) {
                    TextField("Name", text: $gym.name)
                    TextField("Description", text: $gym.description)
                    TextField("Number of Trainers", value: $gym.numberOfTrainers, formatter: NumberFormatter())
                    Button("Save changes") {
                        
                    }
                }
                Section(header: Text("Trainings")) {
                    ForEach(gym.trainings.indices, id: \.self) { index in
                        HStack {
                            TextField("Training Name", text: $gym.trainings[index].name)
                            TextField("Price", value: $gym.trainings[index].price, formatter: NumberFormatter())
                            Toggle("group", isOn: $gym.trainings[index].isGroup)
                                .toggleStyle(.button)
                        }
                    }
                    NavigationLink(destination: AddTrainingView()) {
                        Button("Add New Training") {}
                    }
                }
                
                Section(header: Text("Appointments")) {
                    ForEach(appointments) { appointment in
                        AppointmentRow(appointment: appointments[appointments.firstIndex(where: { $0.id == appointment.id })!])
                    }
                }

                
                
            }
            .navigationTitle("Gym Manager")
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    NavigationLink(destination: AddAppointmentView()) {
                        Image(systemName: "plus")
                    }
                }
            }
        }
    }
    
    struct AppointmentRow: View {
        var appointment: Appointment

        var body: some View {
            VStack(alignment: .leading) {
                Text(appointment.training.name)
                    .font(.headline)
                Text("Type: \(appointment.training.isGroup ? "Group" : "Individual")")
                Text("Date: \(appointment.date)")
                Text("Available: \(appointment.isAvailable ? "Yes" : "No")")
                Button("Cancel") {
                    // TODO: Handle cancel here
                }
                .foregroundStyle(.red)
            }
        }
    }

}

struct AddAppointmentView: View {
    var body: some View {
        // Your add appointment view code here
        Text("Add Appointment View")
    }
}


struct Gym {
    var name: String
    var description: String
    var numberOfTrainers: Int
    var trainings: [Training]
}


#Preview {
    GymManagerView( gym: Gym(name: "New Gym", description: "Best gym in town ready to dominate!", numberOfTrainers: 12, trainings: generateDummyTrainings()), appointments: generateDummyAppointments())
}
