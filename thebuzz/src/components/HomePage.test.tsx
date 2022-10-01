import React from 'react';
import { cleanup, getByTestId, render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import "@testing-library/jest-dom/extend-expect"
import HomePage from './HomePage'

afterEach(() => { 
  cleanup();
})

describe("Homepage Component" , () => {

  test('Render HomePage', () => {
    render(<HomePage />);
    const linkElement = screen.getByText('TheBuzz: Home page');
    expect(linkElement).toBeInTheDocument();
  }) 
  
})