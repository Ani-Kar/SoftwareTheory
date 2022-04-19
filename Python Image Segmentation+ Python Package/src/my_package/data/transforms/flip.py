#Imports
import PIL
from PIL import Image
import numpy as np

class FlipImage(object):
    '''
        Flips the image.
    '''

    def __init__(self, flip_type='horizontal'):
        '''
            Arguments:
            flip_type: 'horizontal' or 'vertical' Default: 'horizontal'
        '''
        self.type=flip_type
        # Write your code here

        
    def __call__(self, image):
        '''
            Arguments:
            image (numpy array or PIL image)

            Returns:
            image (numpy array or PIL image)
        '''

        # Write your code here
        arr = np.array([0])
        if(type(image)==type(arr)):
            image = Image.fromarray(image)
        if(self.type=="horizontal"):
            return image.transpose(PIL.Image.FLIP_LEFT_RIGHT)
        else: 
            return image.transpose(PIL.Image.FLIP_TOP_BOTTOM)

       