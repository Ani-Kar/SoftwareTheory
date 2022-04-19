#Imports 
import string
from turtle import shape
from my_package.data.transforms.rotate import RotateImage
from my_package.data.transforms.rescale import RescaleImage
from my_package.data.transforms.blur import BlurImage
from my_package.data.transforms.flip import FlipImage
from my_package.data.transforms.crop import CropImage
from my_package.data.dataset import Dataset
from my_package.analysis.visualize import plot_visualization
from my_package.model import InstanceSegmentationModel
from unicodedata import category
from matplotlib import cm, transforms
import matplotlib.pyplot as plt
import numpy as np
from PIL import Image
from os import path
import re
import warnings
from numpy import empty_like
#removes standard warnings
warnings.filterwarnings("ignore")

def experiment(annotation_file, segmentor, transforms, outputs):
    '''
        Function to perform the desired experiments

        Arguments:
        annotation_file: Path to annotation file
        segmentor: The image segmentor
        transforms: List of transformation classes
        outputs: path of the output folder to store the images
    '''

    #Create the instance of the dataset.
    
    initial_transforms=[RotateImage(0)]
    dataset_instance=Dataset(annotation_file,initial_transforms)

    #Iterate over all data items.
    #Get the predictions from the segmentor.
    #Draw the segmentation maps on the image and save them.
    file_path = path.abspath(__file__) # full path of your script
    dir_path = path.dirname(file_path) # full path of the directory of your script
    #create directory path
    dir_path1=dir_path+'\\outputs'
    dir_path2=dir_path+'\\output_boxes'
    #stores some temporary lists which will be used later
    dictionary_temp={}
    empty_list=[]
    seg_store = []
    for x in range (dataset_instance.__len__()):
        #iterates through the data for each image
        dictionary_temp=dataset_instance.__getitem__(x)
        seg_store=(segmentor.__call__(dictionary_temp["image"]))
        #creates img objects and calls visualization function to create the correct masked image
        image_temp1=plot_visualization(dictionary_temp['image'],seg_store[0],seg_store[2],seg_store[1])
        image_temp2=plot_visualization(dictionary_temp['image'],seg_store[0],empty_list,empty_list)
        #stores it in the correct page
        image_temp1.save(re.sub(r'[\\\/]', '\\\\', path.join(dir_path1,re.sub(r'[\\\/]','',dataset_instance.data[x]["img_fn"]))), 'PNG')
        image_temp2.save(re.sub(r'[\\\/]', '\\\\', path.join(dir_path2,re.sub(r'[\\\/]','',dataset_instance.data[x]["img_fn"]))), 'PNG')
        


    #Do the required analysis experiments.
    #creates the initial plot and subplots and specifies attributes
    plt.figure(figsize=(0.8,0.8),dpi=150)
    fig, ax = plt.subplots(2,4)
    fig.set_figwidth(16)
    fig.set_figheight(9)
    #titles of image
    list_of_names=['Original','Masked','Flipped','Blurred(3)','Rescaled(2)','Rescaled(0.5)','Rotate(-90)','Rotate(45)']
    for x in range (len(transforms)):
        #creates the instance file and calls visualization
        dataset_instance=Dataset(annotation_file,transforms[x])
        dictionary_temp=dataset_instance.__getitem__(3)
        seg_store=(segmentor.__call__(dictionary_temp["image"]))
        #creates subplots
        if(x==0):
            image_temp=plot_visualization(dictionary_temp['image'],empty_list,empty_list,empty_list)
        else:
            image_temp=plot_visualization(dictionary_temp['image'],seg_store[0],seg_store[2],seg_store[1])
        #adds the to the correct subplot
        ax[int(x/4),x%4].imshow(image_temp)

        string_title = "Image: {}\n".format(x+1)
        string_title=string_title+list_of_names[x]
        ax[int(x/4),x%4].set_title(string_title,fontsize='15')
    
    #saves the analysis     
    fig.savefig('analysis.png',dpi=100)   



    


def main():
    segmentor = InstanceSegmentationModel()
    transform_analysis=[[RotateImage(0)],[RotateImage(0)],[FlipImage()],[BlurImage(3)],[RescaleImage(2*426)],[RescaleImage(213)],[RotateImage(-90)],[RotateImage(45)]]
    experiment("C:\\Users\\ani30\\OneDrive\\Desktop\\VSC Python\\Python_DS_Assignment\\data\\annotations.jsonl", segmentor, transform_analysis,None) # Sample arguments to call experiment()


if __name__ == '__main__':
    main()
