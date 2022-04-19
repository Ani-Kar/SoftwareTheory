#Imports
from PIL import Image,ImageFilter
import numpy as np


class BlurImage(object):
    '''
        Applies Gaussian Blur on the image.
    '''

    def __init__(self, radius):
        '''
            Arguments:
            radius (int): radius to blur
        '''

        # Write your code here
        self.radius_of_blur=radius

    def __call__(self, image):
        '''
            Arguments:
            image (numpy array or PIL Image)

            Returns:
            image (numpy array or PIL Image)
        '''

        # Write your code here
        arr = np.array([0])
        if(type(image)==type(arr)):
            image = Image.fromarray(image)
        return image.filter(ImageFilter.GaussianBlur(self.radius_of_blur))
        

