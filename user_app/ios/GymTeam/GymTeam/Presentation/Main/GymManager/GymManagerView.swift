//
//  GymManagerView.swift
//  GymTeam
//
//  Created by Luka Korica on 1/15/24.
//

import SwiftUI

struct GymManagerView: View {

    @ObservedObject var viewModel: GymManagerViewModel
    
    
    var body: some View {
        NavigationStack {
            Form {
                Section(header: Text("Gym Details")) {
                    TextField("Name", text: $viewModel.gym.name)
                    TextField("Description", text: $viewModel.gym.description)
                    TextField("Number of Trainers", value: $viewModel.gym.numberOfTrainers, formatter: NumberFormatter())
                    Button("Save changes") {
                        
                    }
                }
                Section(header: Text("Trainings")) {
                    ForEach(viewModel.gym.trainings.indices, id: \.self) { index in
                        HStack {
                            TextField("Training Name", text: $viewModel.gym.trainings[index].name)
                            TextField("Price", value: $viewModel.gym.trainings[index].price, formatter: NumberFormatter())
                            Toggle("group", isOn: $viewModel.gym.trainings[index].isGroup)
                                .toggleStyle(.button)
                        }
                    }
                    NavigationLink(destination: AddTrainingView()) {
                        Button("Add New Training") {}
                    }
                }
                
                Section(header: Text("Appointments")) {
                    ForEach(viewModel.appointments) { appointment in
                        AppointmentRow(appointment: viewModel.appointments[viewModel.appointments.firstIndex(where: { $0.id == appointment.id })!]) {
                            viewModel.cancleAppointment(appointment)
                        }
                    }
                }

                
                
            }
            .onAppear {
                viewModel.loadAppointments()
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
        let action: () -> Void

        var body: some View {
            VStack(alignment: .leading) {
                Text(appointment.training.name)
                    .font(.headline)
                Text("Type: \(appointment.training.isGroup ? "Group" : "Individual")")
                Text("Date: \(appointment.date)")
                Text("Available: \(appointment.isAvailable ? "Yes" : "No")")
                Button("Cancel") {
                    action()
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


struct Gym: Codable, Identifiable {
    var id: Int
    var name: String
    var description: String
    var numberOfTrainers: Int
    var trainings: [Training]
}


#Preview {
    GymManagerView( viewModel: GymManagerViewModel(appointments: generateDummyAppointments(), gym: Gym(id: 0, name: "New Gym", description: "Best gym in town ready to dominate!", numberOfTrainers: 12, trainings: generateDummyTrainings()), apiService: AlamofireAPIService(baseUrlString: ""), myUser: generateDummyUsers().first!))
}
