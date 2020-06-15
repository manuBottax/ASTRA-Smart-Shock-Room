#! /bin/bash

curl -X POST -H "Content-Type: application/json" -d "{\"patient_id\" : \"123456\" , \"data_type\" : \"bpm\", \"value\" : \"76\" }" http://localhost:3002/api/data

curl -X POST -H "Content-Type: application/json" -d "{\"patient_id\" : \"123456\" , \"data_type\" : \"blood_pressure_max\", \"value\" : \"120\" }" http://localhost:3002/api/data

curl -X POST -H "Content-Type: application/json" -d "{\"patient_id\" : \"123456\" , \"data_type\" : \"blood_pressure_min\", \"value\" : \"76\" }" http://localhost:3002/api/data

curl -X POST -H "Content-Type: application/json" -d "{\"patient_id\" : \"123456\" , \"data_type\" : \"bpm\", \"value\" : \"84\" }" http://localhost:3002/api/data

curl -X POST -H "Content-Type: application/json" -d "{\"patient_id\" : \"123456\" , \"data_type\" : \"blood_pressure_max\", \"value\" : \"107\" }" http://localhost:3002/api/data

curl -X POST -H "Content-Type: application/json" -d "{\"patient_id\" : \"123456\" , \"data_type\" : \"blood_pressure_min\", \"value\" : \"82\" }" http://localhost:3002/api/data