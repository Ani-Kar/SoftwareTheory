#Imports
from turtle import Shape
from PIL import Image
from numpy import asarray
from unicodedata import category
from os import path
import numpy as np
import random
import json
import json_lines
import re
from my_package.data.transforms.rotate import RotateImage
from my_package.data.transforms.rescale import RescaleImage
from my_package.data.transforms.blur import BlurImage
from my_package.data.transforms.flip import FlipImage
from my_package.data.transforms.crop import CropImage

class Dataset(object):
    '''
        A class for the dataset that will return data items as per the given index
    '''

    def __init__(self, annotation_file, transforms = None):
        '''
            Arguments:
            annotation_file: path to the annotation file
            transforms: list of transforms (class instances)
                        For instance, [<class 'RandomCrop'>, <class 'Rotate'>]
        '''
        self.path=annotation_file
        self.data=[]
        #reads with json lines
        with open(annotation_file,"r") as file:
            for line in json_lines.reader(file):
                self.data.append(line)

        #self.data=json.load(annotation_file)
        self.transforms = transforms
        

    def __len__(self):
        '''
            return the number of data points in the dataset
        '''
        return len(self.data)

    def __getitem__(self, idx):
        '''
            return the dataset element for the index: "idx"
            Arguments:
                idx: index of the data element.

            Returns: A dictionary with:
                image: image (in the form of a numpy array) (shape: (3, H, W))
                gt_png_ann: the segmentation annotation image (in the form of a numpy array) (shape: (1, H, W))
                gt_bboxes: N X 5 array where N is the number of bounding boxes, each 
                            consisting of [class, x1, y1, x2, y2]
                            x1 and x2 lie between 0 and width of the image,
                            y1 and y2 lie between 0 and height of the image.

            You need to do the following, 
            1. Extract the correct annotation using the idx provided.
            2. Read the image, png segmentation and convert it into a numpy array (wont be necessary
                with some libraries). The shape of the arrays would be (3, H, W) and (1, H, W), respectively.
            3. Scale the values in the arrays to be with [0, 1].
            4. Perform the desired transformations on the image.
            5. Return the dictionary of the transformed image and annotations as specified.
        '''
        final = {}
        #image path and png path
        ImagePath = re.sub('\\\\annotations.jsonl','',path.join(self.path,re.sub(r'[\\\/]', '\\\\', self.data[idx]["img_fn"])))
        png_path =  re.sub('\\\\annotations.jsonl','',path.join(self.path,re.sub(r'[\\\/]', '\\\\', self.data[idx]["png_ann_fn"])))
        #gets the image objects
        Img = Image.open(ImagePath)
        png_Img = Image.open(png_path)
        #calls the transform function
        for transform_instance in self.transforms:
            Img = transform_instance.__call__(Img)
        #rolls image
        image = np.array(Img)/255
        image = np.rollaxis(image, 2, 0)
        #converts png to correct format        
        png = np.array(png_Img)/255
        
        temp=png.reshape(*(png.shape), 1)
        png = np.rollaxis(temp, 2, 0)
        
        #stores in result
        final["image"] = image
        final["gt_png_ann"] = png
        final["gt_bboxes"] = []

        for item in self.data[idx]["bboxes"]:
            final["gt_bboxes"].append([item["category"]]+item["bbox"])
        final["gt_bboxes"] = np.array(final["gt_bboxes"])
        return final