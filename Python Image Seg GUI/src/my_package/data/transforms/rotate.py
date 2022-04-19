#Imports
from PIL import Image
import numpy as np

class RotateImage(object):
    '''
        Rotates the image about the centre of the image.
    '''

    def __init__(self, degrees):
        '''
            Arguments:
            degrees: rotation degree.
        '''
        self.rotate_degrees=degrees
        

    def __call__(self, sample):
        '''
            Arguments:
            image (numpy array or PIL image)

            Returns:
            image (numpy array or PIL image)
        '''
        arr = np.array([0])
        if(type(sample)==type(arr)):
            sample = Image.fromarray(sample)
        return sample.rotate(self.rotate_degrees,expand=1)
        
        
    

