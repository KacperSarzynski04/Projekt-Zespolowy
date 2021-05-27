import {Time} from "@angular/common";


export class Training {

  id: string;
  topic: string;
  description: string;
  trainer: string;
  durationInMinutes: number;
  date: Date;
  time: Time;
  trainingImageUrl: string;
  trainingFileUrl: string;
  materials: File;
  imagePath: string;

}

