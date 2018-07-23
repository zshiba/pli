(define append (lambda (xs ys) (cond ((eq xs ()) ys)
                                     (t (cons (car xs) (append (cdr xs) ys))))))

(append (quote (a b c)) (quote (d e f)))

(append (cons (quote a) (cons (quote b) nil)) (cons (quote c) (cons (quote d) (cons (quote e) nil))))
