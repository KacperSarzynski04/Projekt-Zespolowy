import {Time} from "@angular/common";


export class Training {

  id: string;
  topic: String;
  description: String;
  trainer: String;
  durationInMinutes: number;
  date: Date;
  time: Time;
  photo: File;
  materials: File;
  imagePath: String;

}

