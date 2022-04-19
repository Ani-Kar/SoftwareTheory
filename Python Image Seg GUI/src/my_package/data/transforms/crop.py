#Imports
from PIL import Image
from numpy import asarray
import numpy as np
import random

class CropImage(object):
    '''
        Performs either random cropping or center cropping.
    '''

    def __init__(self, shape, crop_type='center'):
        '''
            Arguments:
            shape: output shape of the crop (h, w)
            crop_type: center crop or random crop. Default: center
        '''
        self.shape_of_image=shape
        self.location=crop_type
        # Write your code here

    def __call__(self, image):
        '''
            Arguments:
            image (numpy array or PIL image)

            Returns:
            image (numpy array or PIL image)
        '''
        #checks to see if it is int or tuple
        width, height = image.size
        if(self.location=="center"):
            temp_tuple=(width/2,height/2)
        else:
            #crops randomly and checks to make sure it isnt going out of the image
            temp_tuple=(random.randint(0,width),random.randint(0,height))
        return image.crop((max(temp_tuple[0]-self.shape_of_image[1]/2,0), max(temp_tuple[1]-self.shape_of_image[0]/2,0),min(temp_tuple[0]+self.shape_of_image[1]/2,width), min(temp_tuple[1]+self.shape_of_image[0]/2,height)))
        # Write your code here



        

 