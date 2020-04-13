import React, { useState, useEffect } from 'react';

import './App.css';

function App() {
  const [ patientData, setpatientData ] = useState([]);
  const [ listening, setListening ] = useState(false);

  useEffect( () => {
    if (!listening) {
      const events = new EventSource('http://localhost:3001/events');

      console.log("")

      events.onmessage = (event) => {
        const parsedData = JSON.parse(event.data);

        setpatientData((patientData) => patientData.concat(parsedData));
      };

      setListening(true);
    }
  }, [listening, patientData]);
  
  return (
    <div>
      <h1>ASTRA - Augmented Spaces for TRauma Assistance</h1>
      <p>Schermo di visualizzazione dei dati</p>
    
      <table className="stats-table">
        <thead>
          <tr>
            <th>ID Paziente</th>
            <th>Parametro</th>
            <th>Valore Misurato</th>
          </tr>
        </thead>
        <tbody>
          {
            patientData.map((data, i) =>
              <tr key={i}>
                <td>{data.patient_id}</td>
                <td>{data.data_type}</td>
                <td>{data.value}</td>
              </tr>
            )
          }
        </tbody>
      </table>
    </div>
  );
}

export default App;
